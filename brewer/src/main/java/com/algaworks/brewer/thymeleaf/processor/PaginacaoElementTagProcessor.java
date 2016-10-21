package com.algaworks.brewer.thymeleaf.processor;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

public class PaginacaoElementTagProcessor extends AbstractElementTagProcessor{
	
	private static final String elementName = "paginacao";
	private static final int precedence = 1000;

	public PaginacaoElementTagProcessor(String dialectPrefix) {
		super(TemplateMode.HTML, dialectPrefix, elementName, true, null, false, precedence);
	}

	@Override
	protected void doProcess(ITemplateContext context, IProcessableElementTag tag,
			IElementTagStructureHandler structureHandler) {
		IModelFactory modelFactory = context.getModelFactory();
		IModel model = modelFactory.createModel();

		String pagina = tag.getAttributeValue("page");
		
		model.add(modelFactory.createStandaloneElementTag("th:block", "th:replace", String.format("fragments/Paginacao :: paginacao (%s)", pagina)));
		structureHandler.replaceWith(model, true);
	}

}
